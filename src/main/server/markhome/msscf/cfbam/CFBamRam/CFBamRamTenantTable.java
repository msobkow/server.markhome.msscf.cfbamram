
// Description: Java 11 in-memory RAM DbIO implementation for Tenant.

/*
 *	server.markhome.msscf.CFBam
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfbam.CFBamRam;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfint.CFInt.*;
import server.markhome.msscf.cfbam.CFBam.*;
import server.markhome.msscf.cfbam.CFBamObj.*;
import server.markhome.msscf.cfsec.CFSecObj.*;
import server.markhome.msscf.cfint.CFIntObj.*;
import server.markhome.msscf.cfbam.CFBamObj.*;

/*
 *	CFBamRamTenantTable in-memory RAM DbIO implementation
 *	for Tenant.
 */
public class CFBamRamTenantTable
	implements ICFBamTenantTable
{
	private ICFBamSchema schema;
	private Map< CFSecTenantPKey,
				CFSecTenantBuff > dictByPKey
		= new HashMap< CFSecTenantPKey,
				CFSecTenantBuff >();
	private Map< CFSecTenantByClusterIdxKey,
				Map< CFSecTenantPKey,
					CFSecTenantBuff >> dictByClusterIdx
		= new HashMap< CFSecTenantByClusterIdxKey,
				Map< CFSecTenantPKey,
					CFSecTenantBuff >>();
	private Map< CFSecTenantByUNameIdxKey,
			CFSecTenantBuff > dictByUNameIdx
		= new HashMap< CFSecTenantByUNameIdxKey,
			CFSecTenantBuff >();
	private Map< CFBamRamTenantId32Gen,
				CFBamRamTenantId32Gen > id32Generator
		= new HashMap< CFBamRamTenantId32Gen,
				CFBamRamTenantId32Gen >();
	private Map< CFBamRamTenantId64Gen,
				CFBamRamTenantId64Gen > id64Generator
		= new HashMap< CFBamRamTenantId64Gen,
				CFBamRamTenantId64Gen >();

	public CFBamRamTenantTable( ICFBamSchema argSchema ) {
		schema = argSchema;
	}

	protected int generateNextId32( long argId,
		short argSliceId )
	{
		CFBamRamTenantId32Gen key = new CFBamRamTenantId32Gen();
		key.setRequiredId( argId );
		key.setRequiredSliceId( argSliceId );

		CFBamRamTenantId32Gen useGen = id32Generator.get( key );
		if( useGen == null ) {
			id32Generator.put( key, key );
			useGen = key;
		}

		int retNext = useGen.getNextId();

		return( retNext );
	}

	protected long generateNextId64( long argId,
		short argSliceId )
	{
		CFBamRamTenantId64Gen key = new CFBamRamTenantId64Gen();
		key.setRequiredId( argId );
		key.setRequiredSliceId( argSliceId );

		CFBamRamTenantId64Gen useGen = id64Generator.get( key );
		if( useGen == null ) {
			id64Generator.put( key, key );
			useGen = key;
		}

		long retNext = useGen.getNextId();

		return( retNext );
	}

	public int nextTSecGroupIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		int retval = nextTSecGroupIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public int nextTSecGroupIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		int retNext = generateNextId32( argId,
			(short)117 );
		return( retNext );
	}

	public long nextTSecGrpIncIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextTSecGrpIncIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextTSecGrpIncIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)119 );
		return( retNext );
	}

	public long nextTSecGrpMembIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextTSecGrpMembIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextTSecGrpMembIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)118 );
		return( retNext );
	}

	public long nextMajorVersionIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextMajorVersionIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextMajorVersionIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)205 );
		return( retNext );
	}

	public long nextMinorVersionIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextMinorVersionIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextMinorVersionIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)206 );
		return( retNext );
	}

	public long nextSubProjectIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextSubProjectIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextSubProjectIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)204 );
		return( retNext );
	}

	public long nextTldIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextTldIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextTldIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)200 );
		return( retNext );
	}

	public long nextTopDomainIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextTopDomainIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextTopDomainIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)201 );
		return( retNext );
	}

	public long nextTopProjectIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextTopProjectIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextTopProjectIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)203 );
		return( retNext );
	}

	public long nextChainIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextChainIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextChainIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)1006 );
		return( retNext );
	}

	public long nextEnumTagIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextEnumTagIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextEnumTagIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)1005 );
		return( retNext );
	}

	public long nextIndexColIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextIndexColIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextIndexColIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)1002 );
		return( retNext );
	}

	public long nextParamIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextParamIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextParamIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)1007 );
		return( retNext );
	}

	public long nextRelationColIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextRelationColIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextRelationColIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)1003 );
		return( retNext );
	}

	public long nextScopeIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextScopeIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextScopeIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)1001 );
		return( retNext );
	}

	public long nextValueIdGen( CFSecAuthorization Authorization,
		CFSecTenantPKey pkey )
	{
		long retval = nextValueIdGen( Authorization,
			pkey.getRequiredId() );
		return( retval );
	}

	public long nextValueIdGen( CFSecAuthorization Authorization,
		long argId )
	{
		long retNext = generateNextId64( argId,
			(short)1004 );
		return( retNext );
	}

	public void createTenant( CFSecAuthorization Authorization,
		CFSecTenantBuff Buff )
	{
		final String S_ProcName = "createTenant";
		CFSecTenantPKey pkey = schema.getFactoryTenant().newPKey();
		pkey.setRequiredId( schema.nextTenantIdGen() );
		Buff.setRequiredId( pkey.getRequiredId() );
		CFSecTenantByClusterIdxKey keyClusterIdx = schema.getFactoryTenant().newClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( Buff.getRequiredClusterId() );

		CFSecTenantByUNameIdxKey keyUNameIdx = schema.getFactoryTenant().newUNameIdxKey();
		keyUNameIdx.setRequiredClusterId( Buff.getRequiredClusterId() );
		keyUNameIdx.setRequiredTenantName( Buff.getRequiredTenantName() );

		// Validate unique indexes

		if( dictByPKey.containsKey( pkey ) ) {
			throw new CFLibPrimaryKeyNotNewException( getClass(), S_ProcName, pkey );
		}

		if( dictByUNameIdx.containsKey( keyUNameIdx ) ) {
			throw new CFLibUniqueIndexViolationException( getClass(),
				S_ProcName,
				"TenantUNameIdx",
				keyUNameIdx );
		}

		// Validate foreign keys

		{
			boolean allNull = true;
			allNull = false;
			if( ! allNull ) {
				if( null == schema.getTableCluster().readDerivedByIdIdx( Authorization,
						Buff.getRequiredClusterId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						S_ProcName,
						"Container",
						"TenantCluster",
						"Cluster",
						null );
				}
			}
		}

		// Proceed with adding the new record

		dictByPKey.put( pkey, Buff );

		Map< CFSecTenantPKey, CFSecTenantBuff > subdictClusterIdx;
		if( dictByClusterIdx.containsKey( keyClusterIdx ) ) {
			subdictClusterIdx = dictByClusterIdx.get( keyClusterIdx );
		}
		else {
			subdictClusterIdx = new HashMap< CFSecTenantPKey, CFSecTenantBuff >();
			dictByClusterIdx.put( keyClusterIdx, subdictClusterIdx );
		}
		subdictClusterIdx.put( pkey, Buff );

		dictByUNameIdx.put( keyUNameIdx, Buff );

	}

	public CFSecTenantBuff readDerived( CFSecAuthorization Authorization,
		CFSecTenantPKey PKey )
	{
		final String S_ProcName = "CFBamRamTenant.readDerived";
		CFSecTenantPKey key = schema.getFactoryTenant().newPKey();
		key.setRequiredId( PKey.getRequiredId() );
		CFSecTenantBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTenantBuff lockDerived( CFSecAuthorization Authorization,
		CFSecTenantPKey PKey )
	{
		final String S_ProcName = "CFBamRamTenant.readDerived";
		CFSecTenantPKey key = schema.getFactoryTenant().newPKey();
		key.setRequiredId( PKey.getRequiredId() );
		CFSecTenantBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTenantBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "CFBamRamTenant.readAllDerived";
		CFSecTenantBuff[] retList = new CFSecTenantBuff[ dictByPKey.values().size() ];
		Iterator< CFSecTenantBuff > iter = dictByPKey.values().iterator();
		int idx = 0;
		while( iter.hasNext() ) {
			retList[ idx++ ] = iter.next();
		}
		return( retList );
	}

	public CFSecTenantBuff[] readDerivedByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId )
	{
		final String S_ProcName = "CFBamRamTenant.readDerivedByClusterIdx";
		CFSecTenantByClusterIdxKey key = schema.getFactoryTenant().newClusterIdxKey();
		key.setRequiredClusterId( ClusterId );

		CFSecTenantBuff[] recArray;
		if( dictByClusterIdx.containsKey( key ) ) {
			Map< CFSecTenantPKey, CFSecTenantBuff > subdictClusterIdx
				= dictByClusterIdx.get( key );
			recArray = new CFSecTenantBuff[ subdictClusterIdx.size() ];
			Iterator< CFSecTenantBuff > iter = subdictClusterIdx.values().iterator();
			int idx = 0;
			while( iter.hasNext() ) {
				recArray[ idx++ ] = iter.next();
			}
		}
		else {
			Map< CFSecTenantPKey, CFSecTenantBuff > subdictClusterIdx
				= new HashMap< CFSecTenantPKey, CFSecTenantBuff >();
			dictByClusterIdx.put( key, subdictClusterIdx );
			recArray = new CFSecTenantBuff[0];
		}
		return( recArray );
	}

	public CFSecTenantBuff readDerivedByUNameIdx( CFSecAuthorization Authorization,
		long ClusterId,
		String TenantName )
	{
		final String S_ProcName = "CFBamRamTenant.readDerivedByUNameIdx";
		CFSecTenantByUNameIdxKey key = schema.getFactoryTenant().newUNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredTenantName( TenantName );

		CFSecTenantBuff buff;
		if( dictByUNameIdx.containsKey( key ) ) {
			buff = dictByUNameIdx.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTenantBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		long Id )
	{
		final String S_ProcName = "CFBamRamTenant.readDerivedByIdIdx() ";
		CFSecTenantPKey key = schema.getFactoryTenant().newPKey();
		key.setRequiredId( Id );

		CFSecTenantBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFSecTenantBuff readBuff( CFSecAuthorization Authorization,
		CFSecTenantPKey PKey )
	{
		final String S_ProcName = "CFBamRamTenant.readBuff";
		CFSecTenantBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a015" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFSecTenantBuff lockBuff( CFSecAuthorization Authorization,
		CFSecTenantPKey PKey )
	{
		final String S_ProcName = "lockBuff";
		CFSecTenantBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a015" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFSecTenantBuff[] readAllBuff( CFSecAuthorization Authorization )
	{
		final String S_ProcName = "CFBamRamTenant.readAllBuff";
		CFSecTenantBuff buff;
		ArrayList<CFSecTenantBuff> filteredList = new ArrayList<CFSecTenantBuff>();
		CFSecTenantBuff[] buffList = readAllDerived( Authorization );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a015" ) ) {
				filteredList.add( buff );
			}
		}
		return( filteredList.toArray( new CFSecTenantBuff[0] ) );
	}

	/**
	 *	Read a page of all the specific Tenant buffer instances.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@return All the specific Tenant instances in the database accessible for the Authorization.
	 */
	public CFSecTenantBuff[] pageAllBuff( CFSecAuthorization Authorization,
		Long priorId )
	{
		final String S_ProcName = "pageAllBuff";
		throw new CFLibNotImplementedYetException( getClass(), S_ProcName );
	}

	public CFSecTenantBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		long Id )
	{
		final String S_ProcName = "CFBamRamTenant.readBuffByIdIdx() ";
		CFSecTenantBuff buff = readDerivedByIdIdx( Authorization,
			Id );
		if( ( buff != null ) && buff.getClassCode().equals( "a015" ) ) {
			return( (CFSecTenantBuff)buff );
		}
		else {
			return( null );
		}
	}

	public CFSecTenantBuff[] readBuffByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId )
	{
		final String S_ProcName = "CFBamRamTenant.readBuffByClusterIdx() ";
		CFSecTenantBuff buff;
		ArrayList<CFSecTenantBuff> filteredList = new ArrayList<CFSecTenantBuff>();
		CFSecTenantBuff[] buffList = readDerivedByClusterIdx( Authorization,
			ClusterId );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a015" ) ) {
				filteredList.add( (CFSecTenantBuff)buff );
			}
		}
		return( filteredList.toArray( new CFSecTenantBuff[0] ) );
	}

	public CFSecTenantBuff readBuffByUNameIdx( CFSecAuthorization Authorization,
		long ClusterId,
		String TenantName )
	{
		final String S_ProcName = "CFBamRamTenant.readBuffByUNameIdx() ";
		CFSecTenantBuff buff = readDerivedByUNameIdx( Authorization,
			ClusterId,
			TenantName );
		if( ( buff != null ) && buff.getClassCode().equals( "a015" ) ) {
			return( (CFSecTenantBuff)buff );
		}
		else {
			return( null );
		}
	}

	/**
	 *	Read a page array of the specific Tenant buffer instances identified by the duplicate key ClusterIdx.
	 *
	 *	@param	Authorization	The session authorization information.
	 *
	 *	@param	argClusterId	The Tenant key attribute of the instance generating the id.
	 *
	 *	@return An array of derived buffer instances for the specified key, potentially with 0 elements in the set.
	 *
	 *	@throws	CFLibNotSupportedException thrown by client-side implementations.
	 */
	public CFSecTenantBuff[] pageBuffByClusterIdx( CFSecAuthorization Authorization,
		long ClusterId,
		Long priorId )
	{
		final String S_ProcName = "pageBuffByClusterIdx";
		throw new CFLibNotImplementedYetException( getClass(), S_ProcName );
	}

	public void updateTenant( CFSecAuthorization Authorization,
		CFSecTenantBuff Buff )
	{
		CFSecTenantPKey pkey = schema.getFactoryTenant().newPKey();
		pkey.setRequiredId( Buff.getRequiredId() );
		CFSecTenantBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			throw new CFLibStaleCacheDetectedException( getClass(),
				"updateTenant",
				"Existing record not found",
				"Tenant",
				pkey );
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() ) {
			throw new CFLibCollisionDetectedException( getClass(),
				"updateTenant",
				pkey );
		}
		Buff.setRequiredRevision( Buff.getRequiredRevision() + 1 );
		CFSecTenantByClusterIdxKey existingKeyClusterIdx = schema.getFactoryTenant().newClusterIdxKey();
		existingKeyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		CFSecTenantByClusterIdxKey newKeyClusterIdx = schema.getFactoryTenant().newClusterIdxKey();
		newKeyClusterIdx.setRequiredClusterId( Buff.getRequiredClusterId() );

		CFSecTenantByUNameIdxKey existingKeyUNameIdx = schema.getFactoryTenant().newUNameIdxKey();
		existingKeyUNameIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		existingKeyUNameIdx.setRequiredTenantName( existing.getRequiredTenantName() );

		CFSecTenantByUNameIdxKey newKeyUNameIdx = schema.getFactoryTenant().newUNameIdxKey();
		newKeyUNameIdx.setRequiredClusterId( Buff.getRequiredClusterId() );
		newKeyUNameIdx.setRequiredTenantName( Buff.getRequiredTenantName() );

		// Check unique indexes

		if( ! existingKeyUNameIdx.equals( newKeyUNameIdx ) ) {
			if( dictByUNameIdx.containsKey( newKeyUNameIdx ) ) {
				throw new CFLibUniqueIndexViolationException( getClass(),
					"updateTenant",
					"TenantUNameIdx",
					newKeyUNameIdx );
			}
		}

		// Validate foreign keys

		{
			boolean allNull = true;

			if( allNull ) {
				if( null == schema.getTableCluster().readDerivedByIdIdx( Authorization,
						Buff.getRequiredClusterId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						"updateTenant",
						"Container",
						"TenantCluster",
						"Cluster",
						null );
				}
			}
		}

		// Update is valid

		Map< CFSecTenantPKey, CFSecTenantBuff > subdict;

		dictByPKey.remove( pkey );
		dictByPKey.put( pkey, Buff );

		subdict = dictByClusterIdx.get( existingKeyClusterIdx );
		if( subdict != null ) {
			subdict.remove( pkey );
		}
		if( dictByClusterIdx.containsKey( newKeyClusterIdx ) ) {
			subdict = dictByClusterIdx.get( newKeyClusterIdx );
		}
		else {
			subdict = new HashMap< CFSecTenantPKey, CFSecTenantBuff >();
			dictByClusterIdx.put( newKeyClusterIdx, subdict );
		}
		subdict.put( pkey, Buff );

		dictByUNameIdx.remove( existingKeyUNameIdx );
		dictByUNameIdx.put( newKeyUNameIdx, Buff );

	}

	public void deleteTenant( CFSecAuthorization Authorization,
		CFSecTenantBuff Buff )
	{
		final String S_ProcName = "CFBamRamTenantTable.deleteTenant() ";
		String classCode;
		CFSecTenantPKey pkey = schema.getFactoryTenant().newPKey();
		pkey.setRequiredId( Buff.getRequiredId() );
		CFSecTenantBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			return;
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() )
		{
			throw new CFLibCollisionDetectedException( getClass(),
				"deleteTenant",
				pkey );
		}
			CFBamSchemaDefBuff buffClearTableRelationNarrowed;
			CFBamSchemaDefBuff arrClearTableRelationNarrowed[] = schema.getTableSchemaDef().readDerivedByCTenantIdx( Authorization,
			existing.getRequiredId() );
			for( int idxClearTableRelationNarrowed = 0; idxClearTableRelationNarrowed < arrClearTableRelationNarrowed.length; idxClearTableRelationNarrowed++ ) {
				buffClearTableRelationNarrowed = arrClearTableRelationNarrowed[idxClearTableRelationNarrowed];
				CFBamTableBuff buffTables;
				CFBamTableBuff arrTables[] = schema.getTableTable().readDerivedBySchemaDefIdx( Authorization,
				buffClearTableRelationNarrowed.getRequiredTenantId(),
				buffClearTableRelationNarrowed.getRequiredId() );
				for( int idxTables = 0; idxTables < arrTables.length; idxTables++ ) {
					buffTables = arrTables[idxTables];
					CFBamRelationBuff buffTableRelation;
					CFBamRelationBuff arrTableRelation[] = schema.getTableRelation().readDerivedByRelTableIdx( Authorization,
						buffTables.getRequiredTenantId(),
						buffTables.getRequiredId() );
					for( int idxTableRelation = 0; idxTableRelation < arrTableRelation.length; idxTableRelation++ ) {
						buffTableRelation = arrTableRelation[idxTableRelation];
					{
						CFBamRelationBuff editBuff = schema.getTableRelation().readDerivedByIdIdx( Authorization,
							buffTables.getRequiredTenantId(),
							buffTables.getRequiredId() );
						editBuff.setOptionalNarrowedTenantId( null );
						editBuff.setOptionalNarrowedId( null );
						classCode = editBuff.getClassCode();
						if( classCode.equals( "a835" ) ) {
							schema.getTableRelation().updateRelation( Authorization, editBuff );
						}
						else {
							new CFLibUnsupportedClassException( getClass(),
								S_ProcName,
								"Unrecognized ClassCode \"" + classCode + "\"" );
						}
					}
				
					}
				}
			}
		CFBamSchemaDefBuff buffDelTableChain;
		CFBamSchemaDefBuff arrDelTableChain[] = schema.getTableSchemaDef().readDerivedByCTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelTableChain = 0; idxDelTableChain < arrDelTableChain.length; idxDelTableChain++ ) {
			buffDelTableChain = arrDelTableChain[idxDelTableChain];
			CFBamTableBuff buffTables;
			CFBamTableBuff arrTables[] = schema.getTableTable().readDerivedBySchemaDefIdx( Authorization,
				buffDelTableChain.getRequiredTenantId(),
				buffDelTableChain.getRequiredId() );
			for( int idxTables = 0; idxTables < arrTables.length; idxTables++ ) {
				buffTables = arrTables[idxTables];
					schema.getTableChain().deleteChainByChainTableIdx( Authorization,
						buffTables.getRequiredTenantId(),
						buffTables.getRequiredId() );
			}
		}
		CFBamSchemaDefBuff buffDelTableRelationCol;
		CFBamSchemaDefBuff arrDelTableRelationCol[] = schema.getTableSchemaDef().readDerivedByCTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelTableRelationCol = 0; idxDelTableRelationCol < arrDelTableRelationCol.length; idxDelTableRelationCol++ ) {
			buffDelTableRelationCol = arrDelTableRelationCol[idxDelTableRelationCol];
			CFBamTableBuff buffTables;
			CFBamTableBuff arrTables[] = schema.getTableTable().readDerivedBySchemaDefIdx( Authorization,
				buffDelTableRelationCol.getRequiredTenantId(),
				buffDelTableRelationCol.getRequiredId() );
			for( int idxTables = 0; idxTables < arrTables.length; idxTables++ ) {
				buffTables = arrTables[idxTables];
			CFBamRelationBuff buffTableRelation;
			CFBamRelationBuff arrTableRelation[] = schema.getTableRelation().readDerivedByRelTableIdx( Authorization,
					buffTables.getRequiredTenantId(),
					buffTables.getRequiredId() );
			for( int idxTableRelation = 0; idxTableRelation < arrTableRelation.length; idxTableRelation++ ) {
				buffTableRelation = arrTableRelation[idxTableRelation];
					schema.getTableRelationCol().deleteRelationColByRelationIdx( Authorization,
						buffTableRelation.getRequiredTenantId(),
						buffTableRelation.getRequiredId() );
			}
			}
		}
		CFBamSchemaDefBuff buffDelTableRelation;
		CFBamSchemaDefBuff arrDelTableRelation[] = schema.getTableSchemaDef().readDerivedByCTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelTableRelation = 0; idxDelTableRelation < arrDelTableRelation.length; idxDelTableRelation++ ) {
			buffDelTableRelation = arrDelTableRelation[idxDelTableRelation];
			CFBamTableBuff buffTables;
			CFBamTableBuff arrTables[] = schema.getTableTable().readDerivedBySchemaDefIdx( Authorization,
				buffDelTableRelation.getRequiredTenantId(),
				buffDelTableRelation.getRequiredId() );
			for( int idxTables = 0; idxTables < arrTables.length; idxTables++ ) {
				buffTables = arrTables[idxTables];
					schema.getTableRelation().deleteRelationByRelTableIdx( Authorization,
						buffTables.getRequiredTenantId(),
						buffTables.getRequiredId() );
			}
		}
		CFBamSchemaDefBuff buffDelTable;
		CFBamSchemaDefBuff arrDelTable[] = schema.getTableSchemaDef().readDerivedByCTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelTable = 0; idxDelTable < arrDelTable.length; idxDelTable++ ) {
			buffDelTable = arrDelTable[idxDelTable];
					schema.getTableTable().deleteTableBySchemaDefIdx( Authorization,
						buffDelTable.getRequiredTenantId(),
						buffDelTable.getRequiredId() );
		}
		CFBamSchemaDefBuff buffDelTypeDef;
		CFBamSchemaDefBuff arrDelTypeDef[] = schema.getTableSchemaDef().readDerivedByCTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelTypeDef = 0; idxDelTypeDef < arrDelTypeDef.length; idxDelTypeDef++ ) {
			buffDelTypeDef = arrDelTypeDef[idxDelTypeDef];
					schema.getTableValue().deleteValueByScopeIdx( Authorization,
						buffDelTypeDef.getRequiredTenantId(),
						buffDelTypeDef.getRequiredId() );
		}
					schema.getTableSchemaDef().deleteSchemaDefByCTenantIdx( Authorization,
						existing.getRequiredId() );
					schema.getTableTld().deleteTldByTenantIdx( Authorization,
						existing.getRequiredId() );
		CFSecTSecGroupBuff buffDelIncludedByGroup;
		CFSecTSecGroupBuff arrDelIncludedByGroup[] = schema.getTableTSecGroup().readDerivedByTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelIncludedByGroup = 0; idxDelIncludedByGroup < arrDelIncludedByGroup.length; idxDelIncludedByGroup++ ) {
			buffDelIncludedByGroup = arrDelIncludedByGroup[idxDelIncludedByGroup];
					schema.getTableTSecGrpInc().deleteTSecGrpIncByIncludeIdx( Authorization,
						buffDelIncludedByGroup.getRequiredTenantId(),
						buffDelIncludedByGroup.getRequiredTSecGroupId() );
		}
		CFSecTSecGroupBuff buffDelGrpMembs;
		CFSecTSecGroupBuff arrDelGrpMembs[] = schema.getTableTSecGroup().readDerivedByTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelGrpMembs = 0; idxDelGrpMembs < arrDelGrpMembs.length; idxDelGrpMembs++ ) {
			buffDelGrpMembs = arrDelGrpMembs[idxDelGrpMembs];
					schema.getTableTSecGrpMemb().deleteTSecGrpMembByGroupIdx( Authorization,
						buffDelGrpMembs.getRequiredTenantId(),
						buffDelGrpMembs.getRequiredTSecGroupId() );
		}
		CFSecTSecGroupBuff buffDelGrpIncs;
		CFSecTSecGroupBuff arrDelGrpIncs[] = schema.getTableTSecGroup().readDerivedByTenantIdx( Authorization,
			existing.getRequiredId() );
		for( int idxDelGrpIncs = 0; idxDelGrpIncs < arrDelGrpIncs.length; idxDelGrpIncs++ ) {
			buffDelGrpIncs = arrDelGrpIncs[idxDelGrpIncs];
					schema.getTableTSecGrpInc().deleteTSecGrpIncByGroupIdx( Authorization,
						buffDelGrpIncs.getRequiredTenantId(),
						buffDelGrpIncs.getRequiredTSecGroupId() );
		}
					schema.getTableTSecGroup().deleteTSecGroupByTenantIdx( Authorization,
						existing.getRequiredId() );
		CFSecTenantByClusterIdxKey keyClusterIdx = schema.getFactoryTenant().newClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		CFSecTenantByUNameIdxKey keyUNameIdx = schema.getFactoryTenant().newUNameIdxKey();
		keyUNameIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUNameIdx.setRequiredTenantName( existing.getRequiredTenantName() );

		// Validate reverse foreign keys

		// Delete is valid
		Map< CFSecTenantPKey, CFSecTenantBuff > subdict;

		dictByPKey.remove( pkey );

		subdict = dictByClusterIdx.get( keyClusterIdx );
		subdict.remove( pkey );

		dictByUNameIdx.remove( keyUNameIdx );

	}
	public void deleteTenantByIdIdx( CFSecAuthorization Authorization,
		long argId )
	{
		CFSecTenantPKey key = schema.getFactoryTenant().newPKey();
		key.setRequiredId( argId );
		deleteTenantByIdIdx( Authorization, key );
	}

	public void deleteTenantByIdIdx( CFSecAuthorization Authorization,
		CFSecTenantPKey argKey )
	{
		boolean anyNotNull = false;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		CFSecTenantBuff cur;
		LinkedList<CFSecTenantBuff> matchSet = new LinkedList<CFSecTenantBuff>();
		Iterator<CFSecTenantBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTenantBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTenant().readDerivedByIdIdx( Authorization,
				cur.getRequiredId() );
			deleteTenant( Authorization, cur );
		}
	}

	public void deleteTenantByClusterIdx( CFSecAuthorization Authorization,
		long argClusterId )
	{
		CFSecTenantByClusterIdxKey key = schema.getFactoryTenant().newClusterIdxKey();
		key.setRequiredClusterId( argClusterId );
		deleteTenantByClusterIdx( Authorization, key );
	}

	public void deleteTenantByClusterIdx( CFSecAuthorization Authorization,
		CFSecTenantByClusterIdxKey argKey )
	{
		CFSecTenantBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTenantBuff> matchSet = new LinkedList<CFSecTenantBuff>();
		Iterator<CFSecTenantBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTenantBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTenant().readDerivedByIdIdx( Authorization,
				cur.getRequiredId() );
			deleteTenant( Authorization, cur );
		}
	}

	public void deleteTenantByUNameIdx( CFSecAuthorization Authorization,
		long argClusterId,
		String argTenantName )
	{
		CFSecTenantByUNameIdxKey key = schema.getFactoryTenant().newUNameIdxKey();
		key.setRequiredClusterId( argClusterId );
		key.setRequiredTenantName( argTenantName );
		deleteTenantByUNameIdx( Authorization, key );
	}

	public void deleteTenantByUNameIdx( CFSecAuthorization Authorization,
		CFSecTenantByUNameIdxKey argKey )
	{
		CFSecTenantBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFSecTenantBuff> matchSet = new LinkedList<CFSecTenantBuff>();
		Iterator<CFSecTenantBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFSecTenantBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableTenant().readDerivedByIdIdx( Authorization,
				cur.getRequiredId() );
			deleteTenant( Authorization, cur );
		}
	}

	public void releasePreparedStatements() {
	}
}
